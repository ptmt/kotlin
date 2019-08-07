/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.targets.js.subtargets

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.jetbrains.kotlin.gradle.plugin.AbstractKotlinTargetConfigurator
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinTestRun
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJsCompilation
import org.jetbrains.kotlin.gradle.plugin.whenEvaluated
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsSubtargetTestRun
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsTarget
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsSubTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmResolverPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.npmProject
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest
import org.jetbrains.kotlin.gradle.tasks.createOrRegisterTask
import org.jetbrains.kotlin.gradle.tasks.locateTask
import org.jetbrains.kotlin.gradle.testing.internal.configureConventions
import org.jetbrains.kotlin.gradle.testing.internal.kotlinTestRegistry
import org.jetbrains.kotlin.gradle.utils.lowerCamelCaseName
import org.jetbrains.kotlin.utils.addIfNotNull

abstract class KotlinJsSubTarget(
    val target: KotlinJsTarget,
    private val disambiguationClassifier: String
) : KotlinJsSubTargetDsl {
    val project get() = target.project
    private val nodeJs = NodeJsRootPlugin.apply(project.rootProject)

    val runTaskName = disambiguateCamelCased("run")
    val testTaskName = disambiguateCamelCased("test")

    override val testRuns: NamedDomainObjectContainer<KotlinJsSubtargetTestRun> =
        project.container(KotlinJsSubtargetTestRun::class.java) { name -> KotlinJsSubtargetTestRun(name, this) }

    fun configure() {
        NpmResolverPlugin.apply(project)

        configureTests()
        configureRun()

        target.compilations.all {
            val npmProject = it.npmProject
            it.compileKotlinTask.kotlinOptions.outputFile = npmProject.dir.resolve(npmProject.main).canonicalPath
        }
    }

    private fun disambiguate(name: String): MutableList<String> {
        val components = mutableListOf<String>()

        components.addIfNotNull(target.disambiguationClassifier)
        components.add(disambiguationClassifier)
        components.add(name)
        return components
    }

    protected fun disambiguateCamelCased(name: String): String {
        val components = disambiguate(name)

        return lowerCamelCaseName(*components.toTypedArray())
    }

    private fun configureTests() {
        testRuns.all { configureTestRunDefaults(it) }
        testRuns.create(KotlinTestRun.DEFAULT_TEST_RUN_NAME)
    }

    protected open fun configureTestRunDefaults(testRun: KotlinJsSubtargetTestRun) {
        target.compilations.matching { it.name == KotlinCompilation.TEST_COMPILATION_NAME }.all { compilation ->
            configureTests(testRun, compilation)
        }
    }

    abstract val testTaskDescription: String

    private fun configureTests(testRun: KotlinJsSubtargetTestRun, compilation: KotlinJsCompilation) {
        fun KotlinJsSubtargetTestRun.subtargetTestTaskName(): String = disambiguateCamelCased(
            lowerCamelCaseName(
                name.takeIf { it != KotlinTestRun.DEFAULT_TEST_RUN_NAME },
                AbstractKotlinTargetConfigurator.testTaskNameSuffix
            )
        )

        val testJs = project.createOrRegisterTask<KotlinJsTest>(testRun.subtargetTestTaskName()) { testJs ->
            val compileTask = compilation.compileKotlinTask

            testJs.group = LifecycleBasePlugin.VERIFICATION_GROUP
            testJs.description = testTaskDescription

            testJs.dependsOn(nodeJs.npmInstallTask, compileTask, nodeJs.nodeJsSetupTask)

            testJs.onlyIf {
                compileTask.outputFile.exists()
            }

            testJs.compilation = compilation
            testJs.targetName = listOfNotNull(target.disambiguationClassifier, disambiguationClassifier)
                .takeIf { it.isNotEmpty() }
                ?.joinToString()

            testJs.configureConventions()
        }

        @Suppress("UNCHECKED_CAST")
        testRun.testTask = testJs.getTaskOrProvider() as TaskProvider<KotlinJsTest>

        target.project.kotlinTestRegistry.registerTestTask(
            testJs,
            target.testRuns.maybeCreate(testRun.name).testTask.get() // FIXME eager task instantiation
        )

        project.whenEvaluated {
            testJs.configure {
                if (it.testFramework == null) {
                    configureDefaultTestFramework(it)
                }
            }
        }
    }

    protected abstract fun configureDefaultTestFramework(it: KotlinJsTest)

    fun configureRun() {
        target.compilations.all { compilation ->
            if (compilation.name == KotlinCompilation.MAIN_COMPILATION_NAME) {
                configureRun(compilation)
            }
        }
    }

    protected abstract fun configureRun(compilation: KotlinJsCompilation)

    override fun testTask(body: KotlinJsTest.() -> Unit) {
        project.locateTask<KotlinJsTest>(testTaskName)!!.configure(body)
    }
}
// HELPERS: REFLECT

/*
 * KOTLIN CODEGEN BOX SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-111
 * PLACE: expressions, constant-literals, boolean-literals -> paragraph 1 -> sentence 2
 * NUMBER: 6
 * DESCRIPTION: The use of Boolean literals as the identifier (with backtick) in the packageSimple.
 * NOTE: this test data is generated by FeatureInteractionTestDataGenerator. DO NOT MODIFY CODE MANUALLY!
 */

package `true`

fun box(): String? {
    if (!checkPackageName("true._2_6Kt", "true")) return null

    return "OK"
}

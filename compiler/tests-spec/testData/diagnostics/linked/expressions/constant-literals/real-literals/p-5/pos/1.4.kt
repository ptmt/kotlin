/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-111
 * PLACE: expressions, constant-literals, real-literals -> paragraph 5 -> sentence 1
 * NUMBER: 4
 * DESCRIPTION: A type checking of a real literal with omitted a whole-number part and an exponent mark.
 * HELPERS: checkType
 */

// TESTCASE NUMBER: 1
val value_1 = .0e0 checkType { check<Double>() }

// TESTCASE NUMBER: 2
val value_2 = .0e-0__________________0 checkType { check<Double>() }

// TESTCASE NUMBER: 3
val value_3 = .0E000 checkType { check<Double>() }

// TESTCASE NUMBER: 4
val value_4 = .0E+0___0__0_0 checkType { check<Double>() }

// TESTCASE NUMBER: 5
val value_5 = .0e+0 checkType { check<Double>() }

// TESTCASE NUMBER: 6
val value_6 = .0_______0e00 checkType { check<Double>() }

// TESTCASE NUMBER: 7
val value_7 = .00_0E-000 checkType { check<Double>() }

// TESTCASE NUMBER: 8
val value_8 = .0E+1 checkType { check<Double>() }

// TESTCASE NUMBER: 9
val value_9 = .00e22 checkType { check<Double>() }

// TESTCASE NUMBER: 10
val value_10 = .000e-333 checkType { check<Double>() }

// TESTCASE NUMBER: 12
val value_12 = .0E-5_5_5_5_5 checkType { check<Double>() }

// TESTCASE NUMBER: 15
val value_15 = .0_0_0_0e-88888888 checkType { check<Double>() }

// TESTCASE NUMBER: 19
val value_19 = .345678e00000000001 checkType { check<Double>() }

// TESTCASE NUMBER: 20
val value_20 = <!FLOAT_LITERAL_CONFORMS_ZERO!>.4______567E-100000000000<!> checkType { check<Double>() }

// TESTCASE NUMBER: 21
val value_21 = .5_6e-0 checkType { check<Double>() }

// TESTCASE NUMBER: 22
val value_22 = .65e000000000000 checkType { check<Double>() }

// TESTCASE NUMBER: 23
val value_23 = .7654E+01_____0 checkType { check<Double>() }

// TESTCASE NUMBER: 24
val value_24 = .87__65__43E1 checkType { check<Double>() }

// TESTCASE NUMBER: 25
val value_25 = .98765432e-2 checkType { check<Double>() }

// TESTCASE NUMBER: 26
val value_26 = .0987654321E-3 checkType { check<Double>() }

// TESTCASE NUMBER: 27
val value_27 = .1___1__1_1e4 checkType { check<Double>() }

// TESTCASE NUMBER: 28
val value_28 = .22222E-5 checkType { check<Double>() }

// TESTCASE NUMBER: 29
val value_29 = .33333e+6 checkType { check<Double>() }

// TESTCASE NUMBER: 30
val value_30 = .44444_4E7 checkType { check<Double>() }

// TESTCASE NUMBER: 31
val value_31 = .5555555e8 checkType { check<Double>() }

// TESTCASE NUMBER: 33
val value_33 = .7_77777777E-308 checkType { check<Double>() }

// TESTCASE NUMBER: 35
val value_35 = .99999999999e-3_0_9 checkType { check<Double>() }

// TESTCASE NUMBER: 37
val value_37 = .0____00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 checkType { check<Double>() }

// TESTCASE NUMBER: 38
val value_38 = .000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e-00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000___0 checkType { check<Double>() }

// TESTCASE NUMBER: 39
val value_39 = .000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000e+000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 checkType { check<Double>() }

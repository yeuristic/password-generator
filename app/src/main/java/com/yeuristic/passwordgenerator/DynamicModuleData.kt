package com.yeuristic.passwordgenerator

sealed class DynamicModuleData(val moduleName: String, val landingPageActivityName: String)
object PasswordModule : DynamicModuleData("password_included", "com.yeuristic.password_included.GeneratePasswordActivity")
object PinModule : DynamicModuleData("pin_ondemand", "com.yeuristic.pin_ondemand.GeneratePinActivity")
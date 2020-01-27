package com.sbellanger.annotation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sbellanger.anno.CheckCamelSource
import com.sbellanger.anno.GenerateSource
import kotlinx.android.synthetic.main.activity_main.*

@CheckCamelSource
class MainActivity : AppCompatActivity() {

    @ReflectRuntime(5)
    val reflectTest: Int = 0

    @GenerateSource(5)
    var generateTest: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reflexion_before.text = "Before $reflectTest"
        bindReflectionValue(this)
        reflexion_after.text = "After $reflectTest"

        processor_before.text = "Before $reflectTest"
        bindGenerationValue(this)
        processor_after.text = "After $reflectTest"
    }

    private fun bindReflectionValue(target: Any) {
        val declaredFields = target::class.java.declaredFields

        for (field in declaredFields) {
            for (annotation in field.annotations) {
                when (annotation) {
                    is ReflectRuntime -> {
                        field.isAccessible = true
                        field.set(target, annotation.value)
                    }
                }
            }
        }
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ReflectRuntime(val value: Int)

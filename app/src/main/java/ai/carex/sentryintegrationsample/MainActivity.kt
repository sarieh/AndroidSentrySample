package ai.carex.sentryintegrationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ai.carex.sentryintegrationsample.ui.theme.SentryIntegrationSampleTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SentryIntegrationSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ActionThatCausesTheCrash()
                }
            }
        }
    }
}

@Composable
fun ActionThatCausesTheCrash() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    /*
                    * Note that after removing the following
                    * id("io.sentry.android.gradle") version "3.12.0"
                    * the crash no longer occurs
                    * */
                    val dataClass = SomeDataClassWithKotlinSerialization()
                    dataClass::class.members.find { it.name == "dummy1" }
                }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Don't click me, or I will cause a crash :(\n(Kotlin serializable + kotlin reflection)",
                    textAlign = TextAlign.Center
                )
            }

            Button(
                modifier = Modifier,
                onClick = {
                    val dataClass = SomeDataClassWithJavaSerialization()
                    dataClass::class.members.find { it.name == "dummy1" }
                }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Click me, I will not cause a crash :) \n(Java serializable + kotlin reflection)",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun ActionThatCausesTheCrashPrev() {
    ActionThatCausesTheCrash()
}


@Serializable
data class SomeDataClassWithKotlinSerialization(
    val dummy1: Boolean = false,
    val dummy2: Boolean = true,
    val dummy3: String = "dummy3",
    val dummy4: String = "dummy4",
    val dummy5: Int = 0,
)

data class SomeDataClassWithJavaSerialization(
    val dummy1: Boolean = false,
    val dummy2: Boolean = true,
    val dummy3: String = "dummy3",
    val dummy4: String = "dummy4",
    val dummy5: Int = 0,
) : java.io.Serializable
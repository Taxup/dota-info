package kz.app.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

@ExperimentalAnimationApi
@Composable
fun DefaultScreenUI(
    queue: Queue<UIComponent>,
    progressBarState: ProgressBarState = ProgressBarState.Idle,
    onRemoveHeadFromQueue: () -> Unit,
    content: @Composable () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            content.invoke()

            AnimatedVisibility(visible = queue.isNotEmpty()) {
                queue.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.Dialog) {
                        GenericDialog(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            title = uiComponent.title,
                            description = uiComponent.description,
                            onRemoveHeadFromQueue = onRemoveHeadFromQueue
                        )
                    }
                }
            }
            AnimatedVisibility(visible = progressBarState == ProgressBarState.Loading) {
                CircularIndeterminateProgressBar()
            }
        }
    }

}
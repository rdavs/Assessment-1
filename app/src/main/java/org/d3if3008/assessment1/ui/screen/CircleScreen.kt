package org.d3if3008.assessment1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3008.assessment1.R
import org.d3if3008.assessment1.ui.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CircleScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = colorResource(R.color.light_blue)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.navy),
                    titleContentColor = colorResource(R.color.light_blue),
                )
            )
        }
    ) { paddingValues ->
        Content1(Modifier.padding(paddingValues))
    }
}

@Composable
fun Content1(modifier: Modifier) {
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.navy),
        contentColor = colorResource(id = R.color.light_blue)
    )

    var jariJari by rememberSaveable { mutableStateOf("") }
    var jariJariError by rememberSaveable { mutableStateOf(false) }

    val radioOptions = listOf(
        R.string.luas_lingkaran,
        R.string.keliling_lingkaran
    )
    var rumus by rememberSaveable { mutableIntStateOf(radioOptions[0]) }
    var hasil by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.lingkaran_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = jariJari,
            onValueChange = { jariJari = it },
            label = { Text(text = stringResource(R.string.jari_jari)) },
            isError = jariJariError,
            trailingIcon = { IconPicker1(jariJariError, "cm") },
            supportingText = { ErrorHint1(jariJariError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                RumusOption1(
                    label = stringResource(id = text),
                    isSelected = rumus == text,
                    modifier = Modifier
                        .selectable(
                            selected = rumus == text,
                            onClick = { rumus = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Text(
            text = hasil,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        Row {
            Button(
                onClick = {
                    jariJariError = (jariJari == "" || jariJari == "0")
                    if (jariJariError) return@Button

                    hasil = getHasilLingkaran(rumus, jariJari.toFloatOrNull()) ?: ""
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                colors = customButtonColors
            ) {
                Text(text = stringResource(R.string.hitung))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    shareDataCirle(
                        context = context,
                        jariJari = jariJari,
                        hasil = hasil
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                colors = customButtonColors
            ) {
                Text(text = stringResource(R.string.bagikan_lingkaran))
            }
        }
    }
}

@Composable
fun RumusOption1(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker1(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint1(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun hitungLuasLingkaran(jariJari: Float): Float {
    val pi = 3.14f
    return pi * jariJari * jariJari
}

private fun hitungKelilingLingkaran(jariJari: Float): Float {
    val pi = 3.14f
    return 2 * pi * jariJari
}

private fun getHasilLingkaran(rumus: Int, jariJari: Float?): String? {
    jariJari?.let {
        return when (rumus) {
            R.string.luas_lingkaran -> "Luas: ${hitungLuasLingkaran(it)} cm²"
            R.string.keliling_lingkaran -> "Keliling: ${hitungKelilingLingkaran(it)} cm"
            else -> null
        }
    }
    return null
}

private fun shareDataCirle(context: Context, jariJari: String, hasil: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        val shareMessage = if (hasil.contains("Luas")) {
            context.getString(
                R.string.bagikan_template_luas_lingkaran,
                jariJari,
                hasil.substringAfter("Luas: ").substringBefore(" cm²").toFloatOrNull() ?: 0.0f
            )
        } else {
            context.getString(
                R.string.bagikan_template_keliling_lingkaran,
                jariJari,
                hasil.substringAfter("Keliling: ").substringBefore(" cm").toFloatOrNull() ?: 0.0f
            )
        }
        putExtra(Intent.EXTRA_TEXT, shareMessage)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CircleScreenPreview() {
    Assessment1Theme {
        CircleScreen(rememberNavController())
    }
}

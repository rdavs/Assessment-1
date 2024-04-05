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
fun SquareScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = colorResource(R.color.light_blue)
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.navy),
                    titleContentColor = colorResource(R.color.light_blue),
                )
            )
        }
    ) { paddingValues ->
        Content3(Modifier.padding(paddingValues))
    }
}

@Composable
fun Content3(modifier: Modifier) {
    val customButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.navy),
        contentColor = colorResource(id = R.color.light_blue)
    )

    var sisi by rememberSaveable { mutableStateOf("") }
    var sisiError by rememberSaveable { mutableStateOf(false) }


    val radioOptions = listOf(
        R.string.luas_persegi,
        R.string.keliling_persegi
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
            text = stringResource(id = R.string.persegi_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = sisi,
            onValueChange = { sisi = it },
            label = { Text(text = stringResource(R.string.sisi)) },
            isError = sisiError,
            trailingIcon = { IconPicker3(sisiError, "cm") },
            supportingText = { ErrorHint3(sisiError) },
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
                RumusOption3(
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
                    sisiError = (sisi == "" || sisi == "0")
                    if (sisiError) return@Button

                    hasil = getHasilPersegi(rumus, sisi.toFloatOrNull()) ?: ""
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
                    shareDataSquare(
                        context = context,
                        sisi = sisi,
                        hasil = hasil
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                colors = customButtonColors
            ) {
                Text(text = stringResource(R.string.bagikan_persegi))
            }
        }
    }
}

@Composable
fun RumusOption3(label: String, isSelected: Boolean, modifier: Modifier) {
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
fun IconPicker3(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint3(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun hitungLuasPersegi(sisi: Float): Float {
    return sisi * sisi
}

private fun hitungKelilingPersegi(sisi: Float): Float {
    return 4 * sisi
}

private fun getHasilPersegi(rumus: Int, sisi: Float?): String? {
    sisi?.let {
        return when (rumus) {
            R.string.luas_persegi -> "Luas: ${hitungLuasPersegi(it)} cm²"
            R.string.keliling_persegi -> "Keliling: ${hitungKelilingPersegi(it)} cm"
            else -> null
        }
    }
    return null
}

private fun shareDataSquare(context: Context, sisi: String, hasil: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        val shareMessage = if (hasil.contains("Luas")) {
            context.getString(
                R.string.bagikan_template_luas_persegi,
                sisi,
                hasil.substringAfter("Luas: ").substringBefore(" cm²").toFloatOrNull() ?: 0.0f
            )
        } else {
            context.getString(
                R.string.bagikan_template_keliling_persegi,
                sisi,
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
fun SquareScreenPreview() {
    Assessment1Theme {
        SquareScreen(rememberNavController())
    }
}
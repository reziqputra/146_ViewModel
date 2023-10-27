package com.example.myplaydata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceAround
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myplaydata.data.DataForm
import com.example.myplaydata.data.DataSource.jenis
import com.example.myplaydata.data.DataSource.statt
import com.example.myplaydata.ui.theme.CobViewModel
import com.example.myplaydata.ui.theme.MyPlayDataTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPlayDataTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TampilLayout()
                }
            }
        }
    }
}

@Composable
fun TampilLayout(
    modifier: Modifier = Modifier
){

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    )
    {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            TampilForm()
        }
    }
}

@Composable
fun header(){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        ElevatedCard (elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier.fillMaxWidth()){
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.baseline_arrow_back_24), contentDescription = "", modifier = Modifier.size(25.dp))
                Spacer(modifier = Modifier.width(110.dp))
                Text(text = "Register", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilForm(cobViewModel: CobViewModel = viewModel()) {
    var textNama by remember { mutableStateOf("") }
    var textTlp by remember { mutableStateOf("") }
    var textAlm by remember { mutableStateOf("") }
    var textEml by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dataForm: DataForm
    val uiState by cobViewModel.uiState.collectAsState()
    dataForm = uiState

    header()

    OutlinedTextField(value = textNama,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "UserName") },
        onValueChange = {
            textNama = it
        })

    OutlinedTextField(
        value = textTlp,
        singleLine  = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Telpon") },
        onValueChange = {
            textTlp = it
        }
    )
    OutlinedTextField(
        value = textEml,
        singleLine  = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") },
        onValueChange = {
            textEml = it
        }
    )




    SelectJK(
        option = jenis.map { id -> context.resources.getString(id) },
        onSelectionChanged = { cobViewModel.setJenis(it) })
    SelectST(option = statt.map { ib -> context.resources.getString(ib) },
        onSelectionChanged = { cobViewModel.setStatus(it) })

    OutlinedTextField(value = textAlm,
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Alamat") },
        onValueChange = {
            textAlm = it
        })
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { cobViewModel.insertData(textNama, textTlp,textAlm,textEml,dataForm.sex,dataForm.stat,) })
    {
        Text(
            text = stringResource(R.string.submit),
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    TextHasil(
        jenisnya = cobViewModel.jenisKl,
        statusnya = cobViewModel.stattST,
        emailnya = cobViewModel.namaEml,
        telponnya = cobViewModel.noTlp,
        alamatnya = cobViewModel.alamatUsr,


    )
}

@Composable
fun SelectJK(option: List<String>, onSelectionChanged: (String) -> Unit = {}) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column( verticalArrangement = Arrangement.SpaceEvenly,modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        Text(text = "Jenis Kelamin: ")
            Row(
                modifier = Modifier.padding(3.dp))
            {
                     option.forEach { item ->
                Row (modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }),verticalAlignment = Alignment.CenterVertically){

                    RadioButton(selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        })
                    Text(item)
                }
            }
        }
    }
}

@Composable
fun SelectST(option: List<String>, onSelectionChanged: (String) -> Unit = {}) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column( verticalArrangement = Arrangement.SpaceEvenly,modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        Text(text = "Status: ")
        Row(
            modifier = Modifier.padding(3.dp))
        {
            option.forEach { item ->
                Row (modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }),verticalAlignment = Alignment.CenterVertically){

                    RadioButton(selected = selectedValue == item,
                        onClick = {
                            selectedValue = item
                            onSelectionChanged(item)
                        })
                    Text(item)
                }
            }
        }
    }
}


@Composable
fun TextHasil(emailnya: String, telponnya: String, jenisnya: String, alamatnya: String, statusnya: String,) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    )
    {
        Text(
            text = "Jenis Kelamin : " + jenisnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Status : " + statusnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Alamat : " + alamatnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Email : " + emailnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
        Text(
            text = "Telpon : " + telponnya,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )



    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyPlayDataTheme {
        TampilLayout()
    }
}
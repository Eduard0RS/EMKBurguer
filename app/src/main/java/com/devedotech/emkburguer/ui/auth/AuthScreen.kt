package com.devedotech.emkburguer.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devedotech.emkburguer.R
import com.devedotech.emkburguer.ui.navigation.Screen

@Composable
fun AuthScreen(navController: NavController?, viewModel: AuthViewModel) {
    val tag = "AuthScreen"

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val rememberMeState by viewModel.rememberMe.collectAsState()
    var rememberMe by remember { mutableStateOf(false) }

    val context = LocalContext.current

    LaunchedEffect(rememberMeState) {
        rememberMe = rememberMeState
        if (rememberMeState) {
            viewModel.getSavedCredentials().collect { credentials ->
                login = credentials.first
                password = credentials.second
            }
        }
    }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 30.dp, bottom = 60.dp)
                    .size(90.dp)
            )

            // Campo de E-mail
            TextField(
                value = login,
                label = { SetPlaceHolder(R.string.enter_login) },
                onValueChange = { login = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.MailOutline,
                        contentDescription = stringResource(R.string.sigin_with_google),
                        modifier = Modifier.padding(end = 8.dp),
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                label = { SetPlaceHolder(R.string.enter_password) },
                onValueChange = { password = it },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = stringResource(R.string.enter_password),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Warning else Icons.Default.Add,
                            contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Checkbox "Lembrar-me"
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = {
                        rememberMe = it
                        viewModel.setRememberMe(it, login, password)
                    }
                )
                Text(
                    "Lembrar-me",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(R.color.on_primary)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Esqueci minha senha
            TextButton(onClick = { navController?.navigate(Screen.ForgotPassword.route) }) {
                Text(
                    stringResource(R.string.forgot_password),
                    color = colorResource(R.color.on_primary),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botão de Login
            Button(
                onClick = {
                    viewModel.login(
                        email = login,
                        password = password,
                        rememberMe = rememberMe,
                        onSuccess = {
                            Log.d(tag, "Login bem-sucedido: ${it.token}")
                            navController?.navigate(Screen.Home.route)
                        },
                        onError = {
                            Toast.makeText(
                                context,
                                "Erro ao fazer login: $it",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary),
                    contentColor = colorResource(id = R.color.on_primary)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                SetPlaceHolder(R.string.enter_button)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.secondary),
                    contentColor = colorResource(id = R.color.on_primary)

                )
            ) {
                SetPlaceHolder(R.string.signup_button)
            }

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(thickness = 2.dp)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_pressed),
                    contentColor = colorResource(id = R.color.on_primary)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = stringResource(R.string.sigin_with_google),
                    modifier = Modifier.padding(end = 8.dp),
                )

                (R.string.sigin_with_google)
            }
        }
    }



@Composable
fun SetPlaceHolder(value: Int) {
    Text(stringResource(value))
}


@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(navController = null, AuthViewModel(LocalContext.current))
}


window.onload = function () {
    console.log("Aplicação Frontend - Iniciada");
    obterParametros();
}

function obterParametros() {

    const baseUrl = `${window.location.protocol}//${window.location.host}`;
    const urlObterParametros = `${baseUrl}/api/parametros`;

    // Retorna uma Promise para controle assíncrono
    return new Promise((resolve, reject) => {

        $.ajax({
            url: urlObterParametros,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                console.log("Iniciando requisicao para obter os parametros da Aplicacao");
                mostrarLoading("CARREGANDO...", "Parâmetros");
                // Adicionar headers para debug
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            success: function (data) {

                console.log("Requisição bem-sucedida:", data);

                // Validação robusta dos dados
                if (!data || typeof data !== 'object') {
                    const error = new Error('Resposta invalida da API');
                    console.error('Data inválida:', data);
                    reject(error);
                    return;
                }

                if (data.error) {
                    const error = new Error(`Erro da API: ${data.error || 'Erro desconhecido'}`);
                    console.error('Erro da API:', data.error);
                    reject(error);
                    return;
                }

                processarParametros(data.parametros);
                gerarQRCode(data.parametros.textoQrCode);

            },
            error: function (xhr, textStatus, errorThrown) {

                // Log detalhado do erro
                console.error("Erro completo na requisição:", {
                    status: xhr.status,
                    statusText: xhr.statusText,
                    responseText: xhr.responseText,
                    textStatus: textStatus,
                    errorThrown: errorThrown
                });

                let errorMessage = 'Erro ao buscar seguidores';

                // Tratamento específico por status HTTP
                switch (xhr.status) {
                    case 0:
                        errorMessage = 'Erro de rede - verifique conexão';
                        break;
                    case 400:
                        errorMessage = 'Requisição inválida - verifique parâmetros';
                        break;
                    case 401:
                        errorMessage = 'Access Token inválido ou expirado';
                        break;
                    case 403:
                        errorMessage = xhr.responseText;
                        break;
                    case 404:
                        errorMessage = 'Usuário não encontrado';
                        break;
                    case 429:
                        errorMessage = 'Limite de requisições excedido - tente novamente mais tarde';
                        break;
                    case 500:
                    case 502:
                    case 503:
                        errorMessage = 'Serviço indisponível - tente novamente mais tarde';
                        break;
                    default:
                        errorMessage = `Erro ${xhr.status}: ${xhr.statusText}`;
                }

                const error = new Error(errorMessage);
                error.status = xhr.status;
                error.response = xhr.responseText;
                reject(error);
            },
            complete: function () {
                console.log("Obter parametros finalizado.");
                esconderLoading();
                obterSeguidoresInicialInstagram();
            }
        });
    });
}

function gerarQRCode(url) {

    const text = url.trim();
    const qrcodeContainer = document.getElementById('qrcode');

    // Limpar QR Code anterior
    qrcodeContainer.innerHTML = '';

    try {
        // Gerar novo QR Code
        currentQRCode = new QRCode(qrcodeContainer, {
            text: text,
            width: 200,
            height: 200,
            colorDark: "#000000",
            colorLight: "#ffffff",
            correctLevel: QRCode.CorrectLevel.H
        });

    } catch (error) {
        alert('Erro ao gerar QR Code: ' + error.message);
    }
}

function obterSeguidoresInicialInstagram() {

    // Mostrar loading
    $("#loading-seguidores").show();
    $("#erro-seguidores").hide();

    obterQtdSeguidores().then(function(response) {

        // Sucesso
        console.log("Dados obtidos com sucesso:", response);

    }).catch(function(error) {

        // Erro
        console.error("Falha ao obter seguidores:", error);
        mostrarErro(error);

    }).finally(function() {
        // Sempre executar (sucesso ou erro)
    });

}

function obterSeguidoresInstagramAutomatico() {

    console.log("Atualização automatica de seguidores iniciada...");

    // Mostrar loading
    $("#loading-seguidores").show();
    $("#erro-seguidores").hide();

    obterQtdSeguidoresAutomatico().then(function(response) {

        // Sucesso
        console.log("Dados obtidos com sucesso:", response);

    }).catch(function(error) {

        // Erro
        console.error("Falha ao obter seguidores:", error);

    }).finally(function() {
        // Sempre executar (sucesso ou erro)
    });

}

function obterQtdSeguidores() {

    const ZERO = 0;
    const baseUrl = `${window.location.protocol}//${window.location.host}`;
    const urlObterSeguidores = `${baseUrl}/api/seguidores/teste`;

    // Retorna uma Promise para controle assíncrono
    return new Promise((resolve, reject) => {

        $.ajax({

            url: urlObterSeguidores,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                console.log("Iniciando requisicao para Instagram API");
                mostrarLoading("CARREGANDO...", "Quantidade de seguidores");
                // Adicionar headers para debug
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            success: function (data) {

                console.log("Requisicao bem-sucedida:", data);

                // Validação robusta dos dados
                if (!data || typeof data !== 'object') {
                    const error = new Error('Resposta inválida da API');
                    console.error('Data inválida:', data);
                    reject(error);
                    return;
                }

                if (data.error) {
                    const error = new Error(`Erro da API: ${data.error || 'Erro desconhecido'}`);
                    console.error('Erro da API:', data.error);
                    reject(error);
                    return;
                }

                if (typeof data.totalSeguidores === 'number') {

                    processarQtdSeguidores(data.totalSeguidores);

                } else {

                    const error = new Error('Dados de seguidores não disponíveis na resposta');
                    console.error('Estrutura de dados inesperada:', data);
                    reject(error);

                }
            },
            error: function (xhr, textStatus, errorThrown) {

                // Log detalhado do erro
                console.error("Erro completo na requisição:", {
                    status: xhr.status,
                    statusText: xhr.statusText,
                    responseText: xhr.responseText,
                    textStatus: textStatus,
                    errorThrown: errorThrown
                });

                let errorMessage = 'Erro ao buscar seguidores';

                // Tratamento específico por status HTTP
                switch (xhr.status) {
                    case 0:
                        errorMessage = 'Erro de rede - verifique conexão';
                        break;
                    case 400:
                        errorMessage = 'Requisição inválida - verifique parâmetros';
                        break;
                    case 401:
                        errorMessage = 'Access Token inválido ou expirado';
                        break;
                    case 403:
                        errorMessage = xhr.responseText;
                        break;
                    case 404:
                        errorMessage = 'Usuário não encontrado';
                        break;
                    case 429:
                        errorMessage = 'Limite de requisições excedido - tente novamente mais tarde';
                        break;
                    case 500:
                    case 502:
                    case 503:
                        errorMessage = 'Serviço indisponível - tente novamente mais tarde';
                        break;
                    default:
                        errorMessage = `Erro ${xhr.status}: ${xhr.statusText}`;
                }

                const error = new Error(errorMessage);
                error.status = xhr.status;
                error.response = xhr.responseText;
                reject(error);

                processarQtdSeguidores(ZERO);

            },
            complete: function () {
                console.log("Obter quantidade de seguidores finalizado.");
                esconderLoading();
                // Obter dados automatico
                setInterval(function () {
                    obterSeguidoresInstagramAutomatico();
                }, 15000);
            }
        });
    });
}

function obterQtdSeguidoresAutomatico() {

    const baseUrl = `${window.location.protocol}//${window.location.host}`;
    const urlObterSeguidores = `${baseUrl}/api/seguidores/teste`;

    // Retorna uma Promise para controle assíncrono
    return new Promise((resolve, reject) => {

        $.ajax({

            url: urlObterSeguidores,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                console.log("Iniciando requisicao Automatica para o Instagram API");
                // Adicionar headers para debug
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            success: function (data) {

                console.log("Requisicao bem-sucedida:", data);

                // Validação robusta dos dados
                if (!data || typeof data !== 'object') {
                    const error = new Error('Resposta inválida da API');
                    console.error('Data inválida:', data);
                    reject(error);
                    return;
                }

                if (data.error) {
                    const error = new Error(`Erro da API: ${data.error || 'Erro desconhecido'}`);
                    console.error('Erro da API:', data.error);
                    reject(error);
                    return;
                }

                if (typeof data.totalSeguidores === 'number') {

                    processarQtdSeguidoresAutomatico(data.totalSeguidores);

                } else {

                    const error = new Error('Dados de seguidores não disponíveis na resposta');
                    console.error('Estrutura de dados inesperada:', data);
                    reject(error);

                }
            },
            error: function (xhr, textStatus, errorThrown) {

                // Log detalhado do erro
                console.error("Erro completo na requisição:", {
                    status: xhr.status,
                    statusText: xhr.statusText,
                    responseText: xhr.responseText,
                    textStatus: textStatus,
                    errorThrown: errorThrown
                });

                let errorMessage = 'Erro ao buscar seguidores';

                // Tratamento específico por status HTTP
                switch (xhr.status) {
                    case 0:
                        errorMessage = 'Erro de rede - verifique conexão';
                        break;
                    case 400:
                        errorMessage = 'Requisição inválida - verifique parâmetros';
                        break;
                    case 401:
                        errorMessage = 'Access Token inválido ou expirado';
                        break;
                    case 403:
                        errorMessage = xhr.responseText;
                        break;
                    case 404:
                        errorMessage = 'Usuário não encontrado';
                        break;
                    case 429:
                        errorMessage = 'Limite de requisições excedido - tente novamente mais tarde';
                        break;
                    case 500:
                    case 502:
                    case 503:
                        errorMessage = 'Serviço indisponível - tente novamente mais tarde';
                        break;
                    default:
                        errorMessage = `Erro ${xhr.status}: ${xhr.statusText}`;
                }

                const error = new Error(errorMessage);
                error.status = xhr.status;
                error.response = xhr.responseText;
                reject(error);
            },
            complete: function () {
                console.log("Obter quantidade de seguidores finalizado.");
                esconderLoading();
            }
        });
    });
}

function mostrarErro(error) {
    $("#erro-mensagem").text("Erro: " + error);
    $("#erro-seguidores").show();
    $("#seguidores-container").hide();
}

function mostrarLoading(mensagem, subtitulo) {
    const loading = document.getElementById('loadingPrincipal');
    const textElement = loading.querySelector('.loading-text');
    const subtextElement = loading.querySelector('.loading-subtext');

    if (textElement && mensagem) textElement.textContent = mensagem;
    if (subtextElement && subtitulo) subtextElement.textContent = subtitulo;

    loading.classList.add('show');
}

function esconderLoading() {
    const loadings = document.querySelectorAll('.loading-container, .loading-minimal');
    loadings.forEach(function(loading) {
        loading.classList.remove('show');
    });
}

function processarParametros(parametros) {
    document.title = parametros.titulo;
    document.getElementById("tituloPagina").innerHTML += parametros.tituloPagina;
}

function processarQtdSeguidores(qtd) {
    let el = document.querySelector('.qtdSeguidores');
    let v = qtd;
    let o = new Odometer({
        el: el,
        value: 0,
        theme: 'minimal',
        format: '', // Remove vírgula
    });
    o.render();
    o.update(qtd);
}

function processarQtdSeguidoresAutomatico(qtd) {

    let el = document.querySelector('.qtdSeguidores');
    let o = new Odometer({
        el: el,
        value: qtd,
        theme: 'minimal',
        format: '', // Remove vírgula
    });
    o.render();
    o.update(qtd);

}
# Desafio-Ioasys

As Bibliotecas adicionadas no projeto foram as:

  - Retrofit - para realizar a chamada da API.
  - Gson Converter - para transformar o retorno da API de json para xml.
  - Recycler Viewer e Card View - para criar a lista das informações da API.
  - OKHttp - foi adicionada para utilizar a Função Intercept, já que era necessario enviar 3 Headers de confirmação na API.
  - Live Data - foi adicionada pois o aplicativo foi feito utilizando a arquitetura MVVM e ele foi utilizado para ter um controle das informações.
  - Picasso - foi adicionado para carregar as imagens que vinham na API.

Caso houvesse mais tempo para realizar o desafio, eu teria implementado um sistema para que uma vez feito o login, ele já entrasse direto na pagina de pesquisa, iria fazer isso usando o Shared Preferences, ja que eu utilizo ele para guardar os Headers que vem da API, se houvesse algum headers salvo nele, automaticamente já iria entrar direto na pagina, sem pedir login novamente.

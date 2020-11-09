# SescDev - Maven Project
Sesc Bahia - Programador // Prova Prática

#Cross Site Request Forgery (_csrf)
#Token de autenticação recebido no login

#Registrar Usuários
sescdev/jsonregistro.xhtml
{
    "cpf": "",
    "nome": "",
    "sobrenome": "",
    "email": "",
    "telefone": "",
    "senha": "",
    "admin": true //opcional//
}

#Logar
sescdev/jsonlogin.xhtml
{
    "cpf": "",//Esse//
    "email": "",//ou esse//
    "senha": ""
}

#Registrar Curso
sescdev/admin/reg_cursos.xhtml
{
    "cargahoraria": 0,
    "categoria": "",
    "valor": "9.999,99",
    "titulo": "",
    "descricao": "",
    "_csrf": ""
}

#Registar Inscrição
sescdev/admin/reg_inscricao.xhtml
{
    "idusuario": 0,
    "idcurso": 0,
    "pcg": false,
    "_csrf": ""
}

#Remover dados
sescdev/admin/rem_dados.xhtml
{
	"entidade": "", //usuario, curso, registro//
	"id": 0,
	"_csrf": ""
}

#Lista usuários
sescdev/admin/list_usuarios.xhtml
{
    "_csrf": ""
}

#Lista cursos
sescdev/admin/list_cursos.xhtml
{
    "_csrf": ""
}

#Lista registros
sescdev/admin/list_inscricao.xhtml
{
    "_csrf": ""
}

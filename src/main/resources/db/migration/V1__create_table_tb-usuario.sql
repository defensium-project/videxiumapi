create table if not exists tb_usuario (
    code serial not null,
    code_public varchar(255) not null,
    perfil varchar(255) not null,
    nome varchar(255) not null,
    usuario varchar(255) not null,
    senha varchar(255) not null,
    token varchar(255) not null,
    hash_cadastro varchar(255) null,
    is_conta_verificada boolean null default false,
    created_at timestamp not null default now(),
    updated_at timestamp null,
    deleted_at timestamp null,
    active boolean not null default true,
    constraint pk_usuario primary key (code),
    constraint un_usuario unique (perfil, usuario)
);
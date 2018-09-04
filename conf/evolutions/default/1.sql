# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table categoria (
  id                            integer auto_increment not null,
  nome                          varchar(25) Not null,
  constraint pk_categoria primary key (id)
);

create table livro (
  id                            integer auto_increment not null,
  nome                          varchar(25) Not null,
  descricao                     mediumtext not null,
  caminho                        mediumtext Not null,
  categoria_id                  integer not null,
  usuario_dono_id               integer not null,
  constraint pk_livro primary key (id)
);

create table usuario (
  id                            integer auto_increment not null,
  nome                          varchar(25) NOT NULL,
  email                         varchar(25) NOT NULL,
  senha                         varchar(25) NOT NULL,
  constraint pk_usuario primary key (id)
);

alter table livro add constraint fk_livro_categoria_id foreign key (categoria_id) references categoria (id) on delete restrict on update restrict;
create index ix_livro_categoria_id on livro (categoria_id);

alter table livro add constraint fk_livro_usuario_dono_id foreign key (usuario_dono_id) references usuario (id) on delete restrict on update restrict;
create index ix_livro_usuario_dono_id on livro (usuario_dono_id);


# --- !Downs

alter table livro drop foreign key fk_livro_categoria_id;
drop index ix_livro_categoria_id on livro;

alter table livro drop foreign key fk_livro_usuario_dono_id;
drop index ix_livro_usuario_dono_id on livro;

drop table if exists categoria;

drop table if exists livro;

drop table if exists usuario;


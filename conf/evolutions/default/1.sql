# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table usuario (
  id                            integer auto_increment not null,
  nome                          NOT NULL,
  email                         NOT NULL,
  senha                         NOT NULL,
  constraint pk_usuario primary key (id)
);


# --- !Downs

drop table if exists usuario;


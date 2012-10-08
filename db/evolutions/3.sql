# Play Jug 0.0.3 Schema

# --- !Ups

    alter table Talk alter column teaser longtext;

# --- !Downs

    alter table Talk alter column teaser varchar(255);



CREATE TABLE Nomenclature
(
     Name   varchar,
     Code  int,
    CONSTRAINT  Kode_id  PRIMARY KEY (Code)
);
CREATE TABLE Organization
(
    Org_id int,
    Name_org varchar,
    INN int,
    Account int,
    CONSTRAINT Org_id PRIMARY KEY (Org_id)
);

CREATE TABLE Overhead
(
    Number_overhead int,
    Date_overhead date,
    Organization_sender int,
    CONSTRAINT Number_overhead PRIMARY KEY (Number_overhead),
    CONSTRAINT Organization FOREIGN KEY (Organization_sender)
        REFERENCES Organization (Org_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE Position_overhead
(
    Position_id int,
    Price int,
    Nomenclature int,
    Quantity int,
    Number_overhead int,
    CONSTRAINT Pos_id PRIMARY KEY (Position_id),
    CONSTRAINT Nomenclature_id FOREIGN KEY (Nomenclature)
        REFERENCES Nomenclature (Code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT  Number_id FOREIGN KEY (Number_overhead)
        REFERENCES Overhead (Number_overhead) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
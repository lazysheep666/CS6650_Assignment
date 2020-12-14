CREATE SCHEMA IF NOT EXISTS LiftAPP;
USE LiftAPP;

DROP TABLE IF EXISTS LiftRides;

CREATE TABLE LiftRides (
    ResortID varchar(255),
    DayID int,
    Vertical int,
    SkierID varchar(255),
    time int,
    LiftID varchar(255),
	constraint pk_LiftRides primary key (ResortID, DayID, SkierID, LiftID)
);
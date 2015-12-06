use survey;

drop table surveyResult
;

create table surveyResult (
  id int AUTO_INCREMENT,
  surveyoption varchar (20) NOT NULL,
  votes int NOT NULL,
  constraint id primary key (id)
)
;

insert into surveyResult (id,surveyoption,votes) values (1, 'Dog', 0)
;
insert into surveyResult (id,surveyoption,votes) values (2, 'Cat', 0)
;
insert into surveyResult (id,surveyoption,votes) values (3, 'Bird', 0)
;
insert into surveyResult (id,surveyoption,votes) values (4, 'Snake', 0)
;



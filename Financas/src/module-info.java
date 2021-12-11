module Financas {
	requires javafx.controls;
	requires javafx.fxml;
	requires jakarta.persistence;
	requires java.sql;
	requires javafx.graphics;
	
	opens application;
	opens entity;
	opens form;
}

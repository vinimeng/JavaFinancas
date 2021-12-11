module Financas {
	requires javafx.controls;
	requires javafx.fxml;
	requires jakarta.persistence;
	requires java.sql;
	
	opens application;
	opens entity;
	opens form;
	//opens css to javafx.graphics, javafx.fxml;
}

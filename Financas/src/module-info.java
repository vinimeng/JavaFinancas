module Financas {
	requires javafx.controls;
	requires javafx.fxml;
	requires jakarta.persistence;
	requires transitive java.sql;
	requires transitive javafx.graphics;

	opens application;
	opens entity;
	opens form;

	exports application;
	exports entity;
	exports form;
}

module Financas {
	requires javafx.controls;
	requires javafx.fxml;
	requires jakarta.persistence;
	requires transitive java.sql;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires jfxtras.controls;

	opens application;
	opens entity;
	opens form;
	opens util;

	exports application;
	exports entity;
	exports form;
	exports util;
}

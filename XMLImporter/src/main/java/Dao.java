import xmlmodels.Company;
import xmlmodels.Salary;
import xmlmodels.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    public void insertCompany(Company company) throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres")) {
            final int companyId = getResultSetFromCompany(company, conn);
            for (Staff staff : company.staff) {
                insertStaff(conn, companyId, staff);
                insertSalary(conn, staff);
            }
        }
    }

    public void clearTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres")) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM salary; DELETE FROM staff; DELETE FROM company")) {
                preparedStatement.executeUpdate();
            }

        }
    }

    public List<Company> getAllCompanies() throws SQLException {
        ArrayList<Company> companies = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres")) {
            getCompanies(companies, conn);
            getStaff(companies, conn);
            getSalary(companies, conn);
        }
        return companies;
    }

    private void getSalary(ArrayList<Company> companies, Connection conn) throws SQLException {
        for (Company company : companies) {
            for (Staff staff : company.staff) {
                var resultSet = conn.createStatement().executeQuery("SELECT * FROM salary WHERE staff_id = " + staff.id);
                while (resultSet.next()) {
                    var salary = new Salary();
                    salary.currency = resultSet.getString("currency");
                    salary.value = resultSet.getInt("value");
                    staff.salary = salary;
                }
            }
        }
    }

    private void getStaff(ArrayList<Company> companies, Connection conn) throws SQLException {
        for (Company company : companies) {
            var resultSet = conn.createStatement().executeQuery("SELECT * FROM staff WHERE company_id = " + company.id);
            while (resultSet.next()) {
                var staff = new Staff();
                staff.id = resultSet.getInt("id");
                staff.firstname = resultSet.getString("first_name");
                staff.lastname = resultSet.getString("last_name");
                staff.nickname = resultSet.getString("nick_name");
                company.staff.add(staff);
            }
        }
    }

    private void getCompanies(ArrayList<Company> companies, Connection conn) throws SQLException {
        var resultSet = conn.createStatement().executeQuery("SELECT * FROM company");
        while (resultSet.next()) {
            var company = new Company();
            company.id = resultSet.getInt("id");
            company.name = resultSet.getString("name");
            companies.add(company);
        }
    }


    private void insertSalary(Connection conn, Staff staff) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO salary(staff_id, currency, value) VALUES (?,?,?)")) {
            preparedStatement.setInt(1, staff.id);
            preparedStatement.setString(2, staff.salary.currency);
            preparedStatement.setInt(3, staff.salary.value);
            preparedStatement.executeUpdate();
        }
    }

    private void insertStaff(Connection conn, int companyId, Staff staff) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO staff(id,company_id, first_name, last_name, nick_name) VALUES (?,?,?,?,?)")) {
            preparedStatement.setInt(1, staff.id);
            preparedStatement.setInt(2, companyId);
            preparedStatement.setString(3, staff.firstname);
            preparedStatement.setString(4, staff.lastname);
            preparedStatement.setString(5, staff.nickname);
            preparedStatement.executeUpdate();
        }
    }

    private int getResultSetFromCompany(Company company, Connection conn) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO company(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.name);
            preparedStatement.executeUpdate();
            return getCompanyId(preparedStatement);
        }
    }

    private int getCompanyId(Statement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (int) generatedKeys.getLong(1);
            } else throw new SQLException("No ID obtained.");
        }
    }
}

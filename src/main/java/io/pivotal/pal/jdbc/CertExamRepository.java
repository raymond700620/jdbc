package io.pivotal.pal.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Repository
public class CertExamRepository {
    Logger logger = LoggerFactory.getLogger(CertExamRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final String ALL_SUMMARY = "select 'ALL' as region, m.pivotal_code, " +
            "      count(case when grade='pass' then grade end) pass, " +
            "      count(case when grade='fail' then grade end) fail, " +
            "      count(case when grade='refused' then grade end) refused " +
            "from cert_exam_result r, exam_code_map m " +
            "where r.data_source = m.data_source and r.exam_code = m.exam_code " +
            "and r.exam_date>=?  AND r.exam_date<=? " +
            "group by pivotal_code " +
            "order by pivotal_code ";

    private final String REGION_SUMMARY = "select substr(r.site_region,5) as region, m.pivotal_code, " +
            "      count(case when grade='pass' then grade end) pass, " +
            "      count(case when grade='fail' then grade end) fail, " +
            "      count(case when grade='refused' then grade end) refused " +
            "from cert_exam_result r, exam_code_map m " +
            "where r.data_source = m.data_source  and r.exam_code = m.exam_code " +
            "and r.exam_date>=? and r.exam_date<=?  and substr(r.site_region,5)=? " +
            "group by site_region, pivotal_code " +
            "order by site_region, pivotal_code ";

    private final String COUNTRY_LIST =
            "select distinct site_country from cert_exam_result order by site_country";

    private final String EXAM_DETAIL = "select ID,DATA_SOURCE,CREATE_DATE,UPDATE_DATE,CANDIDATE_EMAIL,CANDIDATE_FIRSTNAME,CANDIDATE_LASTNAME, " +
            "  CANDIDATE_COMPANY,SITE_REGION,SITE_COUNTRY,EXAM_CODE,EXAM_TITLE,EXAM_DATE,SCORE,GRADE " +
            " from cert_exam_result where exam_date>=? and exam_date<=? " +
            "order by exam_date ";
    //+ "limit 50";

    private final String EXAM_RECORD_INSERT = "INSERT INTO cert_exam_result " +
            "(DATA_SOURCE, CREATE_DATE, CANDIDATE_EMAIL, CANDIDATE_FIRSTNAME, CANDIDATE_LASTNAME, CANDIDATE_COMPANY,SITE_REGION,SITE_COUNTRY,EXAM_CODE,EXAM_TITLE,EXAM_DATE, SCORE, GRADE) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";


    @Autowired
    public CertExamRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<CertExamSummary> getCertExamSummary(String startDate, String endDate) {
        return jdbcTemplate.query(ALL_SUMMARY, new Object[]{startDate, endDate},
                mapper);
    }

    public List<CertExamSummary> getCertExamSummaryByRegion(String startDate, String endDate, String region) {
        return jdbcTemplate.query(REGION_SUMMARY, new Object[]{startDate, endDate, region},
                mapper);
    }

    private final RowMapper<CertExamSummary> mapper = (rs, rowNum) ->
            new CertExamSummary(
                    rs.getString("region"),
                    rs.getString("pivotal_code"),
                    rs.getInt("pass"),
                    rs.getInt("fail"),
                    rs.getInt("refused")
            );

    public List<CertExamRecord> getCertExamRecords(String startDate, String endDate) {
        return jdbcTemplate.query(EXAM_DETAIL, new Object[]{startDate, endDate},
                examRecordMapper);
    }

    public List<CertExamRecord> getCertExamRecords(String startDate, String endDate, int limit) {
        String sql = EXAM_DETAIL + " limit " + limit;
        return jdbcTemplate.query(sql, new Object[]{startDate, endDate},
                examRecordMapper);
    }

    private final RowMapper<CertExamRecord> examRecordMapper = (rs, rowNum) ->
            new CertExamRecord(
                    rs.getLong("ID"),
                    rs.getString("DATA_SOURCE"),
                    rs.getDate("CREATE_DATE"),
                    rs.getDate("UPDATE_DATE"),
                    rs.getString("CANDIDATE_EMAIL"),
                    rs.getString("CANDIDATE_FIRSTNAME"),
                    rs.getString("CANDIDATE_LASTNAME"),
                    rs.getString("CANDIDATE_COMPANY"),
                    rs.getString("SITE_REGION"),
                    rs.getString("SITE_COUNTRY"),
                    rs.getString("EXAM_CODE"),
                    rs.getString("EXAM_TITLE"),
                    rs.getDate("EXAM_DATE"),
                    rs.getInt("SCORE"),
                    rs.getString("GRADE")
            );


    public List<String> getCountryList() {
        List<String> data = jdbcTemplate.queryForList(COUNTRY_LIST, String.class);
        return data;
    }

    //TODO: to prevent from SQL Injection, dynamic query should be parsed before sending to server
    //e.g. not allow certain keywords: DELETE, UPDATE, DROP, only allowed certain table name...
    public List<HashMap> getDynamicQueryResult(String sql) {
        return jdbcTemplate.query(sql,
                hashMapper);
    }

    private final RowMapper<HashMap> hashMapper = (rs, rowNum) -> {
        ResultSetMetaData metaData = rs.getMetaData();
        int colCount = metaData.getColumnCount();
        HashMap row = new LinkedHashMap(); //This is to protect the insertion order
        for (int i = 1; i <= colCount; i++) {
            row.put(metaData.getColumnLabel(i), rs.getObject(i));
        }
        return  row;
    };

    //batch insertion - for feed processing
    //TODO: real feed processing should be in stage first then merge
    public int[] insertBatch(final List<CertExamRecord> examRecords){


            return jdbcTemplate.batchUpdate(EXAM_RECORD_INSERT,
                    new BatchPreparedStatementSetter() {

                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, examRecords.get(i).getDataSource());
                            ps.setDate(2, examRecords.get(i).getCreateDate());
                            ps.setString(3, examRecords.get(i).getEmail());
                            ps.setString(4, examRecords.get(i).getFirstName());
                            ps.setString(5, examRecords.get(i).getLastName());
                            ps.setString(6, examRecords.get(i).getCompany());
                            ps.setString(7, examRecords.get(i).getSiteRegion());
                            ps.setString(8, examRecords.get(i).getSiteCountry());
                            ps.setString(9, examRecords.get(i).getExamCode());
                            ps.setString(10, examRecords.get(i).getExamTitle());
                            ps.setDate(11, examRecords.get(i).getExamDate());
                            ps.setInt(12, examRecords.get(i).getScore());
                            ps.setString(13, examRecords.get(i).getGrade());

                        }
                        @Override
                        public int getBatchSize() {
                            return examRecords.size();
                        }
                    });

    }


    private final String INSERT_DYNAMIC_TAB = "insert into dynamic_tab (tab_id, tab_name, dsql, create_date) "
            + " values (?,?,?, NOW())";
    public int addDynamicTab(String tabID, String tabName, String dSql) {
        return jdbcTemplate.update(
                INSERT_DYNAMIC_TAB, tabID, tabName, dSql);
    }

    private final String DELETE_DYNAMIC_TAB = "delete from dynamic_tab where tab_id=?";
    public int deleteDynamicTab(String tabID) {
        return jdbcTemplate.update(
                DELETE_DYNAMIC_TAB, tabID);
    }

    private final String GET_DYNAMIC_TABIDS = "select tab_id from dynamic_tab order by tab_id";
    public List<String> getDynamicTabIDs() {
        return jdbcTemplate.queryForList(GET_DYNAMIC_TABIDS, String.class);
    }

    private final String GET_DYNAMIC_TABIDNAMES = "select tab_id, tab_name from dynamic_tab order by tab_id";
    public List<HashMap> getDynamicTabIDNAMEs() {
        return jdbcTemplate.query(GET_DYNAMIC_TABIDNAMES,
                hashMapper);
    }

    private final String GET_DYNAMIC_TAB_BY_ID = "select  tab_id, tab_name, dsql, create_date, update_date from dynamic_tab where tab_id=?";
    public HashMap getDynamicTabByID(String tabID) {
        logger.info("===>tabID: "+tabID);
        return jdbcTemplate.queryForObject(GET_DYNAMIC_TAB_BY_ID, new Object[]{tabID},
                hashMapper);

    }

}

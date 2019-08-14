package com.createTemplate.api.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

/**
 * solr工具类
 * @author:  
 * @date: 2018年6月9日 下午4:44:59 
 * @version V1.0
 */
public class SolrRule
{
  public static final String AND = " AND ";
  public static final String OR = " OR ";
  public static final String NULL = "  ";
  private StringBuffer para = new StringBuffer();
  private SolrQuery solrQuery = new SolrQuery();

  public SolrRule addObject(String column, Object value, String andOr)
  {
    addAndOr(andOr);
    this.para.append(column + ":" + value + " ");
    return this;
  }

  public SolrRule addNoEqual(String column, Object value, String andOr)
  {
    addAndOr(andOr);
    this.para.append("-" + column + ":" + value + " ");
    return this;
  }

  public SolrRule addBetween(String column, Date value1, Date value2, String andOr)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    addAndOr(andOr);
    this.para.append(column + ":[" + sdf.format(value1) + " TO " + sdf.format(value2) + "] ");
    return this;
  }

  public SolrRule addBetween(String column, Integer value1, Integer value2, String andOr)
  {
    addAndOr(andOr);
    this.para.append(column + ":[" + value1 + " TO " + value2 + "] ");
    return this;
  }

  public SolrRule addSolrRule(SolrRule solrRule, String andOr)
  {
    addAndOr(andOr);
    this.para.append("(");
    this.para.append(solrRule.getParams());
    this.para.append(")");
    return this;
  }

  public String getParams()
  {
    return this.para.toString();
  }

  public SolrRule addIn(String column, String value, String andOr)
  {
    SolrRule childSolrRule = new SolrRule();
    String[] array = value.split("\\,");
    if (array.length == 0) {
		return this;
	}
    for (String temp : array) {
		if ("".equals(childSolrRule.para.toString().trim())) {
			childSolrRule.addObject(column, temp, null);
		} else {
			childSolrRule.addObject(column, temp, " OR ");
		}
	}


    addSolrRule(childSolrRule, andOr);
    return this;
  }

  public SolrRule addAndOr(String andOr)
  {
    this.para.append(" ");
    if (andOr != null) {
		this.para.append(andOr);
	} else if ((andOr == null) && 
      (!("".equals(this.para.toString().trim())))) {
      this.para.append(" AND ");
    }

    this.para.append(" ");
    return this;
  }

  public void setBeginIndex(int page, int rows)
  {
    this.solrQuery.setStart(Integer.valueOf((page - 1) * rows));
    this.solrQuery.setRows(Integer.valueOf(rows));
  }

  public void setIndexLength(int rows) {
    this.solrQuery.setRows(Integer.valueOf(rows));
  }

  public void addAscField(String field)
  {
    this.solrQuery.addSort(field, SolrQuery.ORDER.asc);
  }

  public void addDescField(String field)
  {
    this.solrQuery.addSort(field, SolrQuery.ORDER.desc);
  }

  public SolrQuery getSolrQuery() {
    this.solrQuery.setQuery(this.para.toString());
    return this.solrQuery;
  }

  public SolrRule addParams(String params, String andOr) {
    addAndOr(andOr);
    this.para.append(params);
    return this;
  }
}
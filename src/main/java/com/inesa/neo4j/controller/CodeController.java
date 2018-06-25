package com.inesa.neo4j.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import static org.neo4j.driver.v1.Values.parameters;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inesa.common.entity.RestfulResult;
import com.inesa.common.utils.CommUtils;
import com.inesa.common.utils.Constants;
import com.inesa.neo4j.entity.Code;

/**
 * 自动代码生成Controller
 * 
 * @author sun
 * @version 2018-06-01
 */
@RestController
@RequestMapping(value = "neo4j")
public class CodeController {
    
    private Driver createDrive(){
		return GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "admin" ) );
    }
    
    @RequestMapping(value = "test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		RestfulResult restfulResult = new RestfulResult();
		
		try{			
			Driver driver = createDrive();
	        Session session = driver.session();
	        
	        session.run( "CREATE (a:Person {name: {name}, title: {title}})",
	                parameters( "name", "Arthur001", "title", "King001" ) );

	        StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " +
	                                              "RETURN a.name AS name, a.title AS title",
	                parameters( "name", "Arthur001" ) );
	        
	        while ( result.hasNext() )
	        {
	            Record record = result.next();
	            System.out.println( record.get( "title" ).asString() + " " + record.get( "name" ).asString() + " " + record.get( "id" ).asString() );
	        }
	        
	        session.close();
	        driver.close();
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Code code) {
    	
		RestfulResult restfulResult = new RestfulResult();
		
		try{	
			Driver driver = createDrive();
	        Session session = driver.session();
	        
	        StatementResult result =  session.run( "CREATE (a:" + code.getLabel() + " {" + code.getProperty() + "}) return a");
	        
	        while (result.hasNext())
	        {
	            Record record = result.next();
		        restfulResult.setData(record.fields().get(0).value().toString().replace("node<", "").replace(">", ""));
	        }
	        
	        session.close();
	        driver.close();
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}

	@RequestMapping(value = "update")
	public void update(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Code code) {
    	
		RestfulResult restfulResult = new RestfulResult();
		
		try{	
			Driver driver = createDrive();
	        Session session = driver.session();
	        
	        StatementResult result = session.run("MATCH (a:" + code.getLabel() + ") WHERE a." + code.getWhere() + " SET a." + code.getUpdate() + " return COUNT(a)");
	        
	        while (result.hasNext())
	        {
	            Record record = result.next();
		        restfulResult.setData(record.fields().get(0).value().toString());
	        }
	        
	        session.close();
	        driver.close();
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}

    @RequestMapping(value = "delete")
	public void delete(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Code code) {
		RestfulResult restfulResult = new RestfulResult();
		
		try{			
			Driver driver = createDrive();
	        Session session = driver.session();
	        session.run( "match (n) where ID(n) = " + code.getId() +" detach delete n");
	        
	        session.close();
	        driver.close();
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}

    @RequestMapping(value = "search")
	public void search(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Code code) {
		RestfulResult restfulResult = new RestfulResult();
		
		try{			
			Driver driver = createDrive();
	        Session session = driver.session();

	        StatementResult result = session.run("MATCH " + code.getProperty() +
	        									" MATCH " + code.getRelation() +
	        									" WHERE " + code.getWhere() +
	                                              " RETURN " + code.getResult());
	        List<String> resultList = new ArrayList<String>();
	        while ( result.hasNext() )
	        {
	            Record record = result.next();
	            resultList.add(record.get("id").toString() + " " + record.get("name").toString());
	        }
	        
	        session.close();
	        driver.close();
	        
	        restfulResult.setData(resultList);
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}

    @RequestMapping(value = "relate")
	public void relate(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Code code) {
		RestfulResult restfulResult = new RestfulResult();
		
		try{
			Driver driver = createDrive();
	        Session session = driver.session();
	        
	        session.run("MATCH (a:" + code.getNodeFromLabel() + "), (b:" + code.getNodeToLabel() + ") " +
	        		"WHERE ID(a) = " + code.getNodeFromId() + " AND ID(b) = " + code.getNodeToId()
	        		+ " CREATE (a)-[:" + code.getRelation() + "]->(b)");
	        
	        session.close();
	        driver.close();
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}

	//private static final String DB_PATH = "D:/neo4j/data/databases/graph.db";
	
	//GraphDatabaseService graphDb;
	
	/*@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Code code) {
    	
		RestfulResult restfulResult = new RestfulResult();
		
		try{		
			if (graphDb == null)
				this.graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));
			
			registerShutdownHook(graphDb);
			
			Transaction tx = graphDb.beginTx();
		
			Node node = this.graphDb.createNode();
			Label label = DynamicLabel.label(code.getLabel());
			node.addLabel(label);
			node.setProperty("Name", code.getProperty());

            tx.success();
            
            restfulResult.setData(node.getId());
			
		}catch(Exception e){
			restfulResult.setResult(Constants.RESULT_STATE_ERROR);
			restfulResult.setMessage(e.getMessage());
		}
		
    	CommUtils.printDataJason(response, restfulResult);
	}*/
    
/*    private void registerShutdownHook(final GraphDatabaseService graphDB){
        //关闭寄存器
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                graphDB.shutdown();
            }
        });
    }*/
}
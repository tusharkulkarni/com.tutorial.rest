package com.tutorial.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.codehaus.jettison.json.JSONArray;

import java.sql.*;
import com.tutorial.rest.dao.*;
import com.spoonacular.test.*;
//import com.test.air.*;
import com.test.air.JarTest;

/**
 * This is the root path for our restful api service
 * In the web.xml file, we specified that /api/* need to be in the URL to
 * get to this class.
 * 
 * We are versioning the class in the URL path.  This is the first version v1.
 * Example how to get to the root of this api resource:
 * http://localhost:7001/com.youtube.rest/api/v1/status
 * 
 * @author 308tube
 *
 */
@Path("/v1/status/")
public class V1_status {

	private static final String api_version = "00.01.00"; //version of the api

	/**
	 * This method sits at the root of the api.  It will return the name
	 * of this api.
	 * 
	 * @return String - Title of the api
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnTitle() {
		JSONArray jsonArray=null;
		 SpoonacularTest st = new SpoonacularTest();
		 try{
		  jsonArray= st.getRecipeList(null);
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		return jsonArray.toString();
	}

	/**
	 * This method will return the version number of the api
	 * Note: this is nested one down from the root.  You will need to add version
	 * into the URL path.
	 * 
	 * Example:
	 * http://localhost:7001/com.youtube.rest/api/v1/status/version
	 * 
	 * @return String - version number of the api
	 */
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version:</p>" + api_version;
	}

	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getEmployeeDetails()throws Exception{
		PreparedStatement query = null;
		String str = null;
		String returnString = null;
		Connection conn = null;
		Response response = null;
		try{
			conn = TestDAO.getConn();
			query = conn.prepareStatement("SELECT id, name FROM emp");
			ResultSet rs = query.executeQuery();
			while(rs.next()){
				//Retrieve by column name
				int id  = rs.getInt("id");
				String name = rs.getString("name");
				str = "id : " + id + " Name : " + name;
			}
			query.close();
			returnString = "<p> Parts Details</p>"+ "\n" + str;

			response = Response.ok(returnString).build();
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			if(null != query){
				query.close();
				}
			
		}
		return response;
	}

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.Task;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author donya
 */
@Stateless
@Path("goals")
public class GoalREST {
    
    @EJB
    private GoalFacade goalFacade;
    
    @EJB
    private TaskFacade taskFacase;

    public GoalREST() {
    }
    
    @GET
    @Produces({"application/json"})
    public List<Goal> findAll() {
        System.out.println("Getting Goals via RESTFUL webService**********");
        return goalFacade.findAll();
    }
 
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Goal findGoalById(@PathParam("id") Integer id) {
        return goalFacade.find(id);
    
    }
    @GET
    @Path("tasks/{id}")
    @Produces({"application/json"})
    public List<Task> findTasksForSpesificGoal(@PathParam("id") Integer id) {
        System.out.println("Getting Tasks via RESTFUL webService**********");
        return taskFacase.getTasksByGoalId(id);
    }
 
    @POST
    public void test(@FormParam("name") String name) {
        System.out.println("************Mou is calling me");
        System.out.println("My name is "+name);
    }
}

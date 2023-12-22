package main.java.io.jenkins.plugins.sample;

import java.io.IOException;
import java.io.PrintWriter;

import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.StaplerRequest;
import javax.servlet.ServletException;


import hudson.model.Run;
import jenkins.model.RunAction2;

public class HelloWorldAction implements RunAction2 {

    private String name;
    private transient Run run;

    public HelloWorldAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getIconFileName() {
        return "document.png";
    }

    @Override
    public String getDisplayName() {
        return "Greeting";
    }

    @Override
    public String getUrlName() {
        return "greeting";
    }

    /*
    public HttpResponse doIndex() {
        // Return an anonymous class implementing HttpResponse
        return new HttpResponse() {
            @Override
            public void generateResponse(StaplerRequest req, StaplerResponse rsp, Object node) throws IOException, ServletException {
                // Setting the content type to HTML
                rsp.setContentType("text/html;charset=UTF-8");
                
                // Writing a simple HTML response
                PrintWriter writer = rsp.getWriter();
                writer.println("<html><body>");
                writer.println("<h1>Greeting</h1>");
                writer.println("<p>Hello, " + name + "!</p>"); // Use the name field
                writer.println("</body></html>");
            }
        };
    } 
    */

    @Override
    public void onAttached(Run<?, ?> run) {
        this.run = run; 
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run; 
    }

    public Run getRun() { 
        return run;
    }
}

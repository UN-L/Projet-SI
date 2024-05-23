package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.Computer;
import hudson.model.TaskListener;
import hudson.slaves.ComputerListener;

@Extension
public class ListenerMyComputer extends ComputerListener {

    @Override
    public void onOnline(Computer c, TaskListener listener) {
        listener.getLogger().println("Node is now online: " + c.getName());
        // Your custom code here
    }

    @Override
    public void onTemporarilyOnline(Computer c) {
        System.out.println("Node is temporarily online: " + c.getName());
        // Your custom code here
    }

    @Override
    public void onLaunchFailure(Computer c, TaskListener listener) {
        listener.getLogger().println("Node failed to launch: " + c.getName());
        // Your custom code here
    }
}


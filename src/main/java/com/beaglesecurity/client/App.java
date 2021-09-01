package com.beaglesecurity.client;

import java.io.IOException;
import java.util.List;

import com.beaglesecurity.entities.Project;
import com.beaglesecurity.entities.ProjectWithApplication;

public class App {

	public static void main(String[] args) throws IOException {
		BeagleSecurityClient client= BeagleSecurityClientBuilder.instance()
		.withAPIToken("j69czobljo3ozp2knze4v1554eekp3r9")
		.build();
		List<ProjectWithApplication> projects = client.getAllProjectsWithApplications();
		System.out.println(projects.size());
	}

}

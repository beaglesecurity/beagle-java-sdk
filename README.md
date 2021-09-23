<p align="center">
  <a href="https://github.com/beaglesecurity/beagle-java-sdk">
    <img src="beagle-logo.png" alt="Logo" width="80">
  </a>
  <h3 align="center">Developer guide for Beagle Security SDK</h3>
  
  <p align="center">
    This library can be used to automate penetration testing of your website using Beagle Security
    <br />
    <br />
  </p>
 </p>
 
## Table of Contents

- [Getting Started](#getting-started)
- [Basic Usage](#basic-usage)
- [License](#license)

    
## Getting Started

Beagle Security is a web application & API penetration testing tool that helps you to identify vulnerabilities on your website before hackers exploit them. There are two mechanisms available for automating the process. 

- Using REST APIs
- Using SDK

You can find the complete set of REST APIs in the Beagle Security <a href="https://beaglesecurity.com/developer/apidoc">API documentation page</a>.


## Basic Usage

Once you have resolved the dependencies, you can start using the SDK by creating BeagleSecurityClient to communicate with the Beagle Security platform. The below code will fetch all the projects available under a user. 


```java
public static void main(String[] args) throws IOException {
   // Beagle Security client for communicating with platform
	BeagleSecurityClient client= 
			BeagleSecurityClientBuilder.instance()
			// This token will be generated from beagle security settings->Access Token
			.withAPIToken("j69czobljo3ozp2knze4v1554eekp3r9")
			.build();
	// Gets all the projects and its applications under a user 
	List<ProjectWithApplication> projects = client.getAllProjects();
}
```

### Starting a test

The below code snippet can be used to trigger a new penetration test.

```java
public static void main(String[] args) throws IOException {
   // Beagle Security client for communicating with platform
	BeagleSecurityClient client= 
			BeagleSecurityClientBuilder.instance()
			// This token will be generated from beagle security settings->Access Token
			.withAPIToken("j69czobljo3ozp2knze4v1554eekp3r9")
			.build();
	// The application token will be available in the application settings page
	String applicationToken = "6mkakhiyhxlonol42v87e9cs2gbyarpg";
	// Start a test
	StartTest startTestResult = client.startTest(applicationToken);
	// If started, return a result token
	System.out.println(startTestResult.getResultToken());
}
```

### Getting status of a running test

The below code snippet can be used to get the status of a running test.

```java
public static void main(String[] args) throws IOException {
   // Beagle Security client for communicating with platform
	BeagleSecurityClient client= 
			BeagleSecurityClientBuilder.instance()
			// This token will be generated from beagle security settings->Access Token
			.withAPIToken("j69czobljo3ozp2knze4v1554eekp3r9")
			.build();
	// The application token will be available in the application settings page
	String applicationToken = "6mkakhiyhxlonol42v87e9cs2gbyarpg";
	// This will be available from the start test API or getTestSessions() or 
	// getTestRunningSessions() APIs
	String resultToken = "wagywiof6m76j1jwgzt8wgjtkuhiuxnv";
	// Start a test
	TestStatus testStatus = client.getTestStatus(applicationToken, resultToken);
	System.out.println("Test Status : " + testStatus.getStatus());
	System.out.println("Test Progress : " + testStatus.getProgress());
}
```

### Getting test result json

The below code snippet can be used to get the test result once a penetration test is completed. 

```java
public static void main(String[] args) throws IOException {
   // Beagle Security client for communicating with platform
	BeagleSecurityClient client= 
			BeagleSecurityClientBuilder.instance()
			// This token will be generated from beagle security settings->Access Token
			.withAPIToken("j69czobljo3ozp2knze4v1554eekp3r9")
			.build();
	// The application token will be available in the application settings page
	String applicationToken = "6mkakhiyhxlonol42v87e9cs2gbyarpg";
	// This will be available from the start test API or getTestSessions() or 
	// getTestRunningSessions() APIs
	String resultToken = "wagywiof6m76j1jwgzt8wgjtkuhiuxnv";
	// Gets the test result
	String jsonResult = client.getTestResultJson(applicationToken, resultToken);
}
```

## License

This library is Licensed under the MIT License.


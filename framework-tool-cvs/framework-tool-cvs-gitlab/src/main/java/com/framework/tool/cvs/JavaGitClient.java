package com.framework.tool.cvs;

import org.gitlab.api.AuthMethod;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.CreateUserRequest;
import org.gitlab.api.models.GitlabAccessLevel;
import org.gitlab.api.models.GitlabProject;
import org.gitlab.api.models.GitlabSession;
import org.gitlab.api.models.GitlabUser;

import java.io.IOException;
import java.util.List;

public class JavaGitClient {

    public static void main(String[] args) throws IOException {
        String hostUrl = "http://gitlab.simple.com";
        String apiToken = "HHhQ25psbMqybj87HBgD";
        TokenType tokenType = TokenType.PRIVATE_TOKEN;
        AuthMethod method = AuthMethod.URL_PARAMETER;
        /*GitlabSession session = GitlabAPI.connect(hostUrl, "root", "Hing12345678");
        String token = session.getPrivateToken();*/
        GitlabAPI gitlabAPI = GitlabAPI.connect(hostUrl, apiToken, tokenType, method);

        //GitlabProject project = gitlabAPI.createProject("rpa-controller22");

        List<GitlabProject> allProjects = gitlabAPI.getAllProjects();
        for (GitlabProject p : allProjects) {
            System.out.println(p.getName());
        }

        CreateUserRequest createUserRequest=new CreateUserRequest("client-name","client-name","223114517411@qq.com");
        createUserRequest.setPassword("123456789");
        GitlabUser user1 = gitlabAPI.createUser(createUserRequest);
        System.out.println(user1);

        GitlabUser user = gitlabAPI.getUser();
        GitlabProject gitlabProject=new GitlabProject();
        gitlabProject.setName("aip-create-5");
        gitlabProject.setOwner(gitlabAPI.getUser());
        gitlabProject.setDefaultBranch("master");
        gitlabProject.setPublic(true);
        gitlabProject.setDescription("first create");

        GitlabProject project = gitlabAPI.createProject(gitlabProject);
        gitlabAPI.addProjectMember(project,user, GitlabAccessLevel.Master);

        System.out.println(project);
    }
}

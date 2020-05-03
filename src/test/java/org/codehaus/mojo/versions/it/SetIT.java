package org.codehaus.mojo.versions.it;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

import com.soebes.itf.jupiter.extension.MavenIT;
import com.soebes.itf.jupiter.extension.MavenOptions;
import com.soebes.itf.jupiter.extension.MavenProject;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@MavenIT
class SetIT
{

    private static final String VERSIONS_PLUGIN = "${project.groupId}:${project.artifactId}:${project.version}";

    @Nested
    @MavenProject
    @TestMethodOrder( OrderAnnotation.class )
    @DisplayName( "Test case related to https://github.com/mojohaus/versions-maven-plugin/issues/83" )
    class set_001
    {

        @MavenTest(options = MavenOptions.NON_RECURSIVE, goals = {VERSIONS_PLUGIN + ":set"},
                   systemProperties = {"newVersion=2.0"} )
        @Order( 10 )
        void first( MavenExecutionResult result )
        {
            assertThat( result ).isSuccessful();
        }

        @MavenTest( options = MavenOptions.NON_RECURSIVE, goals = {VERSIONS_PLUGIN + ":set"},
                    systemProperties = {"newVersion=2.0", "groupId=*", "artifactId=*", "oldVersion=*"} )
        @Order( 20 )
        void second( MavenExecutionResult result)
        {
            assertThat( result ).isSuccessful();
        }
    }

    @MavenTest( goals = {VERSIONS_PLUGIN + ":set"},
                systemProperties = {"newVersion=2.3"} )
    void set_001_issue_76 (MavenExecutionResult result) {
        assertThat( result ).isSuccessful();
//            .project().hasModule( "xx" ).hasPom().
//              .hasPom().withVersion("2.3")
//                       .hasDependencies().with;

        /*
        invoker.goals=${project.groupId}:${project.artifactId}:${project.version}:set -DnewVersion=2.3
invoker.buildResult=success

         */
    }
}
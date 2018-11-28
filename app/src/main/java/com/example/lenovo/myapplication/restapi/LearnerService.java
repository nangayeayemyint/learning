package com.example.lenovo.myapplication.restapi;

        import com.example.lenovo.myapplication.models.SInstruction;
        import com.example.lenovo.myapplication.models.SLevel;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Path;
        import retrofit2.http.Query;

public interface LearnerService {
    @GET("spaces/{space_id}/environments/{environment_id}/entries")
    Call<List<SLevel>> getLevelList(@Path("space_id") String spaceId, @Path("environment_id") String environmentId, @Query("content_type") String contentType, @Query("access_token") String accessToken);

    @GET("spaces/{space_id}/environments/{environment_id}/entries?content_type=instruction&select=fields")
    Call<List<SInstruction>> getInstructionList(@Path("space_id") String spaceId, @Path("environment_id") String environmentId, @Query("links_to_entry") String linksToEntry, @Query("access_token") String accessToken);
}

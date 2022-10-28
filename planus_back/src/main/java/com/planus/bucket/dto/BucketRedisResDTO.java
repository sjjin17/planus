package com.planus.bucket.dto;

import com.planus.db.entity.Bucket;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.List;

@RedisHash("tripId")
@Getter
@ToString
@NoArgsConstructor
public class BucketRedisResDTO {
    @Id
    private Long tripId;

    private List<BucketResDTO> bucketResDTO;

    @Builder
    public BucketRedisResDTO(Long tripId, List<BucketResDTO> bucketResDTOList) {
        this.tripId = tripId;
        this.bucketResDTO = bucketResDTOList;
    }


}

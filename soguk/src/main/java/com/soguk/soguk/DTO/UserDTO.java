package com.soguk.soguk.DTO;

import com.soguk.soguk.models.Entry;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserDTO {
    private String id;
    private String nick;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private List<Entry> likedEntries;
}

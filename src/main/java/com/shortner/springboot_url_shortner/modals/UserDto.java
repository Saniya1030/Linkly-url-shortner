package com.shortner.springboot_url_shortner.modals;

import java.io.Serializable;

public record UserDto (Long id, String name)implements Serializable {
}

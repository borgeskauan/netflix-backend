package org.contoso.netflix.playlist.domain.entity;

import lombok.Getter;

@Getter
public enum SystemPlaylist {
    WATCH_LATER("watch_later"),
    FAVORITES("favorites"),
    WATCHED("watched"),
    LIKED("liked"),
    DISLIKED("disliked");

    private final String id;

    SystemPlaylist(String id) {
        this.id = id;
    }

    public static boolean isSystemPlaylist(String playlistId) {
        for (SystemPlaylist systemPlaylist : values()) {
            if (systemPlaylist.getId().equals(playlistId)) {
                return true;
            }
        }

        return false;
    }
}

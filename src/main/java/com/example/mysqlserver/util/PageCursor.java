package com.example.mysqlserver.util;

import java.util.List;

public record PageCursor<T>(
        CursorRequest cursorRequest,
        List<T> body
) {
    public CursorRequest nextCursorRequest() {
        return cursorRequest.next(cursorRequest.key());
    }
}

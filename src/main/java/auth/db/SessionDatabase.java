package auth.db;

import auth.AuthUserDetailsWithUuid;
import infra.db.Database;

public interface SessionDatabase extends Database<AuthUserDetailsWithUuid> {
}

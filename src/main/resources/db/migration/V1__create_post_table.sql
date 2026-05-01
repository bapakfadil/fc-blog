CREATE TABLE post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    body text NOT NULL,
    slug VARCHAR(255) NOT NULL,
    is_published BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME,
    published_at DATETIME,
    deleted_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
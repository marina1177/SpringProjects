CREATE TABLE IF NOT EXISTS  statistics (
                                        id SERIAL PRIMARY  KEY,
                                        operation_time TIMESTAMP WITH TIME ZONE NOT NULL,
                                        topic VARCHAR (20) NOT NULL,
                                        success_load_messages INT,
                                        fail_load_messages INT
);

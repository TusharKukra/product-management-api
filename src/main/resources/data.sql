-- Create the table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT NOT NULL,
    image_file_name VARCHAR(2083) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert data into the table
INSERT INTO products (name, brand, category, price, description, image_file_name, created_at) VALUES
('iPhone 12', 'Apple', 'Phones', 969, 'Apple iPhone 12, 64GB, Black Unlocked and compatible with any carrier of choice on GSM and CDMA networks. Tested for battery health and guaranteed to come with a battery that exceeds 90% of original capacity.', 'iphone12.png', CURRENT_TIMESTAMP),
('iPhone 13', 'Apple', 'Phones', 1299, 'Apple iPhone 13 Pro 512GB Graphite Unlocked and compatible with any carrier of choice on GSM and CDMA networks.', 'iphone13.png', CURRENT_TIMESTAMP),
('iPhone 14', 'Apple', 'Phones', 969.8, 'Apple iPhone 14 Pro 128GB Purple.', 'iphone14.png', CURRENT_TIMESTAMP),
('Galaxy S21', 'Samsung', 'Phones', 799, 'Samsung Galaxy S21 5G with a dynamic AMOLED display and 128GB storage.', 'samsungs21.png', CURRENT_TIMESTAMP),
('Pixel 7', 'Google', 'Phones', 599, 'Google Pixel 7 5G with a powerful Tensor G2 chip and a 50MP camera.', 'pixel7.png', CURRENT_TIMESTAMP),
('MacBook Air M1', 'Apple', 'Laptops', 999, 'Apple MacBook Air with M1 chip, 13.3-inch Retina display, 8GB RAM, and 256GB SSD.', 'macbookm1.png', CURRENT_TIMESTAMP),
('Dell XPS 13', 'Dell', 'Laptops', 1149, 'Dell XPS 13 with 13.4-inch InfinityEdge display, Intel i7, and 16GB RAM.', 'dellxps13.png', CURRENT_TIMESTAMP),
('Sony WH-1000XM4', 'Sony', 'Accessories', 349, 'Sony WH-1000XM4 Wireless Noise Cancelling Over-Ear Headphones.', 'sony.png', CURRENT_TIMESTAMP),
('iPad Pro', 'Apple', 'Tablets', 1099, 'Apple iPad Pro 11-inch with M2 chip, 256GB storage, and Liquid Retina display.', 'ipadpro.png', CURRENT_TIMESTAMP),
('Galaxy Tab S8', 'Samsung', 'Tablets', 899, 'Samsung Galaxy Tab S8 with 12.4-inch Super AMOLED display and 256GB storage.', 'galaxytab.png', CURRENT_TIMESTAMP);

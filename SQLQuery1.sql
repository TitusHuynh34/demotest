USE master
GO

DROP DATABASE IF EXISTS c2212lswing
CREATE DATABASE c2212lswing
GO

USE c2212lswing
GO

DROP TABLE IF EXISTS customer
CREATE TABLE customer
(
id INT PRIMARY KEY IDENTITY,
fullname NVARCHAR(50),
gender BIT,
picture VARCHAR(200),
dob DATE
)
GO

INSERT INTO customer(fullname,gender,picture,dob)
VALUES (N'titus huỳnh',1,'images/1.png','2023-12-27')
GO 20
GO

INSERT INTO customer(fullname,gender,picture,dob)
VALUES (N'nguyên',0,'images/1.png','1982-04-27')
GO 20
GO

INSERT INTO customer(fullname,gender,picture,dob)
VALUES (N'hahaha',0,'images/1.png','2024-01-05')
GO 20
GO

--Viết store trả về toàn bộ dữ liệu bảng customer

CREATE PROC getAllCus
AS
BEGIN
	SELECT * FROM customer
END
GO

--Update Customer
CREATE PROC updateCus
@fullname NVARCHAR(50), @gender BIT, @picture VARCHAR(200), @dob DATE, @id int
AS
BEGIN
	UPDATE customer
	SET fullname = @fullname , gender = @gender, picture = @picture, dob = @dob
	WHERE id = @id
END
GO
--insert customer
CREATE PROC insertCus
@fullname NVARCHAR(50), @gender BIT, @picture VARCHAR(200), @dob DATE

AS
BEGIN
	INSERT INTO customer(fullname, gender, picture, dob)
	VALUES (@fullname, @gender, @picture, @dob)
END
GO

-- Xóa Customer
CREATE PROC deleteCus
@id int
AS
BEGIN
    DELETE FROM customer
    WHERE id = @id
END
GO

--phân trang
-- đếm số dòng trong 1 bảng để tính số trang
CREATE PROC countCus
AS
BEGIN
	SELECT COUNT(id) total FROM customer
END
GO

--lấy từ dòng nào đến dòng nào, và lấy tất cả bao nhiêu dòng
CREATE PROC getCus
@pageNumber INT, @rowOfPage INT
AS
BEGIN
	SELECT * FROM customer
	ORDER BY id
	OFFSET (@pageNumber -1) * @rowOfPage ROWS
	FETCH NEXT @rowOfPage ROWS ONLY
END
GO
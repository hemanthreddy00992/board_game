# VPC
resource "aws_vpc" "my_vpc" {
  cidr_block       = var.vpc_cidr
  instance_tenancy = "default"
  enable_dns_hostnames = true

  tags = {
    Name = "my_vpc"
  }
}

# Internet Gateway
resource "aws_internet_gateway" "my_gw" {
  vpc_id = aws_vpc.my_vpc.id

  tags = {
    Name = "my_gw"
  }
}

# availability zones
data "aws_availability_zones" "my_zones" {
  state = "available"
}

# public subnet
resource "aws_subnet" "my_pub" {
  vpc_id     = aws_vpc.my_vpc.id
  cidr_block = var.pub_cidr

  tags = {
    Name = "my_pub"
  }
}

# route table
resource "aws_route_table" "my_rt" {
  vpc_id = aws_vpc.my_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.my_gw.id
  }

  tags = {
    Name = "my_rt"
  }
}

# attach route table to subnet
resource "aws_route_table_association" "my_a" {
  subnet_id      = aws_subnet.my_pub.id
  route_table_id = aws_route_table.my_rt.id
}
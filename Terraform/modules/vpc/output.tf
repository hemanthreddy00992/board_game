output "vpc_id" {
  value = aws_vpc.my_vpc.id
}

output "pub_id" {
  value = aws_subnet.my_pub.id
}


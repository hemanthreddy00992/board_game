# Ec2

data "aws_ami" "ubuntu" {
  most_recent = true

  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
  }
  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }
  owners = ["099720109477"] # Canonical
}


resource "aws_instance" "jenkins" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = var.instance_type
  vpc_security_group_ids = [var.sg_id]
  associate_public_ip_address = true
  subnet_id = var.pub_id
  user_data = file("${path.module}/../../root/jenkins-script.sh")

  tags = {
    Name = "my_ec2"
  }
}

/*
resource "aws_instance" "Sonarqube" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = var.instance_type
  vpc_security_group_ids = [var.sg_id]
  associate_public_ip_address = true
  subnet_id = var.pub_id
  user_data = file("${path.module}/../../root/sonarqube.sh")

  tags = {
    Name = "my_ec2"
  }
}

resource "aws_instance" "nexus" {
  ami           = data.aws_ami.ubuntu.id
  instance_type = var.instance_type
  vpc_security_group_ids = [var.sg_id]
  associate_public_ip_address = true
  subnet_id = var.pub_id
  user_data = file("${path.module}/../../root/nexus.sh")

  tags = {
    Name = "nexus"
  }
}
*/


terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

resource "aws_instance" "mb_ec2" {
  ami           = data.aws_ami.ubuntu.id
  subnet_id     = var.private_subnet_id
  key_name      = "myblog"
  instance_type = "t2.micro"
  security_groups = [var.security_group_id] //security_group은 list or set 구조 필요

  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = "30"
    volume_type = "gp3"
  }

  tags = {
    Name = "mb_ec2"
  }
}

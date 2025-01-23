terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

/*----- private ec2 eip -----*/
resource "aws_eip" "mb_private_ec2" {
  instance = aws_instance.mb_ec2.id

  tags = {
    Name = "mb_private_ec2_eip"
  }
}

/*----- private ec2 + eip -----*/
resource "aws_eip_association" "mb_private_ec2_eip" {
  instance_id = aws_instance.mb_ec2.id
  allocation_id = aws_eip.mb_private_ec2.id
}

/*----- private subnet ec2 -----*/
resource "aws_instance" "mb_ec2" {
  ami           = data.aws_ami.ubuntu.id
  subnet_id     = var.private_subnet_id
  key_name      = "myblog"
  instance_type = "t2.micro"
  security_groups = [var.private_security_group_id] //security_group은 list or set 구조 필요

  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = "30"
    volume_type = "gp3"
  }

  tags = {
    Name = "mb_private_ec2"
  }
}

/*-------------------------------*/

/*----- bastion ec2 eip -----*/
resource "aws_eip" "mb_ec2_eip" {
  instance = aws_instance.mb_bastion_ec2.id

  tags = {
    Name = "mb_bastion_ec2_eip"
  }
}
/*------------------*/

/*----- bastion ec2 + eip -----*/
resource "aws_eip_association" "mb_bastion_ec2_eip" {
  instance_id = aws_instance.mb_bastion_ec2.id
  allocation_id = aws_eip.mb_ec2_eip.id
}
/*---------------------*/

/*----- Bastion EC2 -----*/
resource "aws_instance" "mb_bastion_ec2" {
  ami = data.aws_ami.ubuntu.id
  subnet_id = var.public_subnet_id
    key_name = "myblog"
  instance_type = "t2.micro"
  security_groups = [var.bastion_security_group_id]

  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = "10"
    volume_type = "gp2"
  }

  tags = {
    Name = "mb_bastion_ec2"
  }
}
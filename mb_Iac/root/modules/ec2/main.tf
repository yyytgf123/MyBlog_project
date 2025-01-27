terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

/*---------------------------------------------------*/
# /*----- private ec2 eip -----*/
# resource "aws_eip" "mb_private_ec2" {
#   count = length(aws_instance.mb_ec2)
#
#   instance = aws_instance.mb_ec2[count.index].id
#
#   tags = {
#     Name = "mb_private_ec2_eip_${count.index}"
#   }
# }
#
# /*----- private ec2 + eip -----*/
# resource "aws_eip_association" "mb_private_ec2_eip" {
#   count = length(aws_instance.mb_ec2)
#
#   instance_id = aws_instance.mb_ec2[count.index].id
#   allocation_id = aws_eip.mb_private_ec2[count.index].id
# }

/*----- private subnet ec2 -----*/
# resource "aws_launch_template" "mb_ec2_launch_template" {
#   name_prefix = "mb_ec2_launch_template"
#   image_id = data.aws_ami.ubuntu.id
#   key_name      = "myblog"
#   instance_type = "t2.micro"
#
#   network_interfaces { // launch_template에서는 network_interface에 감싸줘야함
#     security_groups = [var.private_security_group_id]
#   }
#
#   iam_instance_profile {
#     name = aws_iam_instance_profile.eks_worker_node_profile.name
#   }
#
#   block_device_mappings {
#     device_name = "/dev/sda1"
#
#     ebs {
#       volume_size = "30"
#       volume_type = "gp3"
#     }
#   }
#
#   tag_specifications {
#     resource_type = "instance"
#
#     tags = {
#       Name = "mb_private_ec2"
#     }
#   }
#
#   user_data = base64encode(<<EOF
#     #!/bin/bash
#     set -o xtrace
#     /etc/eks/bootstrap.sh ${var.eks_cluster_name}
#   EOF
#   )
#
# }
/*-------------------------------*/

/*----- private subnet ec2 instance profile --------*/
# resource "aws_iam_instance_profile" "eks_worker_node_profile" {
#   name = "eks_worker_node_profile"
#   role = var.eks_workernode_role
# }
/*--------------------------------------------------*/
/*---------------------------------------------------*/


/*-------------------------------------------------*/
/*----- bastion ec2 eip -----*/
resource "aws_eip" "mb_ec2_eip" {
  instance = aws_instance.mb_bastion_ec2.id

  tags = {
    Name = "mb_bastion_ec2_eip"
  }
}
/*---------------------------*/

/*----- bastion ec2 + eip -----*/
resource "aws_eip_association" "mb_bastion_ec2_eip" {
  instance_id = aws_instance.mb_bastion_ec2.id
  allocation_id = aws_eip.mb_ec2_eip.id
}
/*-----------------------------*/

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
/*------------------------*/
/*-------------------------------------------------*/

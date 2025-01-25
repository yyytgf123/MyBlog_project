terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

/*----- auto scaling -----*/
resource "aws_autoscaling_group" "mb_autoscaling" {
  launch_template {
    id = var.mb_ec2_launch_template
    version = "$Latest"
  }

  vpc_zone_identifier = var.eks_private_subnet_ids

  target_group_arns = [var.mb_alb_tg]

  max_size = 3
  min_size = 1
  desired_capacity = 1

  tag {
    key                 = "kubernetes.io/cluster/${var.mb_eks_cluster}"
    propagate_at_launch = true
    value               = "owned"
  }

  tag {
    key                 = "Name"
    propagate_at_launch = true
    value               = "mb_ec2_instance"
  }
}
/*-------------------------*/
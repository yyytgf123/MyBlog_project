terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

/*----- ALB -----*/
resource "aws_lb" "mb_alb" {
  name = "mbalb"
  internal = false
  load_balancer_type = "application"
  security_groups = [var.alb_security_group_id]
  subnets = var.public_subnet_ids
}

resource "aws_lb_target_group" "mb_alb_target_group" {
  name = "mbalbtg" // _ 허용 x
  port = 80
  protocol = "HTTP"
  vpc_id = var.vpc_id
  target_type = "instance"

  health_check {
    path                = "/health" # 애플리케이션의 Health Check 경로
    interval            = 30
    timeout             = 5
    healthy_threshold   = 2
    unhealthy_threshold = 2
  }
}

resource "aws_lb_listener" "mb_alb_listener" {
  load_balancer_arn = aws_lb.mb_alb.arn
  port = 80
  protocol = "HTTP"

  default_action {
    type = "forward"
    target_group_arn = aws_lb_target_group.mb_alb_target_group.arn
  }
}
/*----------------*/
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

/*----- route53 -----*/
resource "aws_route53_zone" "mb_route53_zone" {
  force_destroy = true
  name = "cbnu.store"
}

resource "aws_route53_record" "mb_route53_a_record" {
  name    = "www.cbnu.store"
  type    = "A"
  zone_id = aws_route53_zone.mb_route53_zone.zone_id
  # ttl = 300

  alias { // alb - A record -> Alias로 생성
    evaluate_target_health = false
    name                   = var.alb_dn
    zone_id                = var.alb_zone_id
  }
}